export interface Usuario {
    idUsuario?: number;
    nickname: string;
    password: string;
    correo: string;
    fechaNacimiento: string | Date;
    pais: string;
    imagenPerfil?: any; //para archivos binarios
    rol: 'ADMIN' | 'EMPRESA' | 'GAMER';
    bibliotecaPublica?: boolean;
    estado?: boolean;
  }
  
  // Request para login
  export interface LoginRequest {
    correo: string;
    password: string;
  }
  
  // Request para registro
  export interface RegistroRequest {
    nickname: string;
    password: string;
    correo: string;
    fechaNacimiento: string;
    pais: string;
    rol?: 'GAMER';
    bibliotecaPublica?: boolean;
  }
  
  // Response del login
  export interface UsuarioResponseDTO {
    idUsuario: number;
    nickname: string;
    correo: string;
    fechaNacimiento: string | Date;
    pais: string;
    rol: 'ADMIN' | 'EMPRESA' | 'GAMER';
    bibliotecaPublica: boolean;
  }