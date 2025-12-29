export interface Categoria {
    idCategoria: number;
    nombre: string;
  }
  
  export interface VideojuegoDetalle {
    idJuego: number;
    titulo: string;
    descripcion: string;
    precio: number;
    requisitosMinimos: string;
    clasificacionEdad: string;
    fechaLanzamiento: string;
    idEmpresa: number;
    nombreEmpresa: string;

    categorias: Categoria[];
  }
  