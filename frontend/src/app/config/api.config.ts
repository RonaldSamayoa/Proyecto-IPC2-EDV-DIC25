export class ApiConfig {

    public static readonly BASE_URL = 'http://localhost:8080/gestion-juegos';
  
    public static readonly USUARIOS = {
      LOGIN: `${ApiConfig.BASE_URL}/usuarios?accion=login`,
      REGISTRO: `${ApiConfig.BASE_URL}/usuarios?accion=registro`
    };
  
    public static readonly BIBLIOTECA = {
      LISTAR: `${ApiConfig.BASE_URL}/biblioteca`
    };

    public static readonly VIDEOJUEGOS = {
      LISTAR: `${ApiConfig.BASE_URL}/videojuegos`,
      DETALLE: `${ApiConfig.BASE_URL}/videojuegos/detalle`
    };

    public static readonly COMPRAS = {
      COMPRAR: `${ApiConfig.BASE_URL}/compras`
    };
    
  }
  