export interface VideojuegoCreate {
    titulo: string;
    descripcion: string;
    precio: number;
    requisitosMinimos: string;
    clasificacionEdad: string;
    fechaLanzamiento?: string;
    idEmpresa: number;
  }
  