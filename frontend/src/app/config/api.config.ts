export class ApiConfig {

    public static readonly BASE_URL = 'http://localhost:8080/gestion-juegos';
  
    public static readonly USUARIOS = {
      LOGIN: `${ApiConfig.BASE_URL}/usuarios?accion=login`,
      REGISTRO: `${ApiConfig.BASE_URL}/usuarios?accion=registro`
    };
  
  }
  