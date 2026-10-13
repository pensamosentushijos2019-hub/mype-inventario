package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class ImagenProducto {
    private String id;
    private String productoId;
    private String url;
    private Boolean esPrincipal = false;

    @com.fasterxml.jackson.annotation.JsonCreator
    public ImagenProducto() {
        this.id = UUID.randomUUID().toString();
    }

    public ImagenProducto(String productoId, String url, Boolean esPrincipal) {
        this();
        this.productoId = productoId;
        this.url = url;
        this.esPrincipal = esPrincipal;
        if (this.productoId == null || this.productoId.isBlank()) throw new IllegalArgumentException("productoId es obligatorio");
        if (this.url == null || this.url.isBlank()) throw new IllegalArgumentException("url es obligatoria");
    }

    public String getId() { return id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }

    public void marcarComoPrincipal() { this.esPrincipal=true; }
}
