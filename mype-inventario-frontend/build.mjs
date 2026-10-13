import {mkdir,copyFile} from 'node:fs/promises';
import {fileURLToPath} from 'node:url';
import path from 'node:path';
const dir=path.dirname(fileURLToPath(import.meta.url));
const destination=path.resolve(dir,'../mype-inventario-backend/src/main/resources/static');
await mkdir(destination,{recursive:true});
for(const file of ['index.html','styles.css','app.js']) await copyFile(path.join(dir,file),path.join(destination,file));
console.log('Frontend copiado al backend. Ejecuta Maven para empaquetarlo en el WAR.');
