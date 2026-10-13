import http from 'node:http';
import {readFile} from 'node:fs/promises';
import path from 'node:path';
import {fileURLToPath} from 'node:url';
const dir=path.dirname(fileURLToPath(import.meta.url));
const upstream=new URL(process.env.BACKEND_URL||'http://localhost:8080/mypeinventario');
const files={'/':'index.html','/index.html':'index.html','/app.js':'app.js','/styles.css':'styles.css'};
http.createServer(async(req,res)=>{
 const pathname=new URL(req.url,'http://localhost').pathname;
 if(pathname.startsWith('/api/')){
  const proxy=http.request({hostname:upstream.hostname,port:upstream.port||80,path:upstream.pathname.replace(/\/$/,'')+req.url,method:req.method,headers:{...req.headers,host:upstream.host}},response=>{const responseHeaders={...response.headers};
   if(responseHeaders['set-cookie'])responseHeaders['set-cookie']=responseHeaders['set-cookie'].map(cookie=>cookie.replace(/;\s*Path=[^;]*/i,'; Path=/'));
   res.writeHead(response.statusCode,responseHeaders);response.pipe(res);});
  proxy.on('error',()=>{if(!res.headersSent)res.writeHead(502,{'content-type':'application/json'});res.end(JSON.stringify({error:'Backend no disponible. Inícialo en el puerto 3000.'}));});req.pipe(proxy);return;
 }
 const filename=files[pathname];if(!filename){res.writeHead(404);res.end('No encontrado');return;}
 try{const body=await readFile(path.join(dir,filename));res.writeHead(200,{'content-type':filename.endsWith('.js')?'text/javascript; charset=utf-8':filename.endsWith('.css')?'text/css; charset=utf-8':'text/html; charset=utf-8'});res.end(body);}catch{res.writeHead(500);res.end('No se pudo leer el recurso');}
}).listen(5173,'127.0.0.1',()=>console.log('Frontend: http://localhost:5173 (API: '+upstream.origin+')'));
