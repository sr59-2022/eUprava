
export interface GradjaninDTO {
  id: number;
  ime: string;
  prezime: string;
  radniStatus: RadniStatus;
  imaPotvrdu: boolean;
  potvrdaId?: number;
}

export enum RadniStatus {
  ZAPOSLEN = 'ZAPOSLEN',
  NEZAPOSLEN = 'NEZAPOSLEN',
  STUDENT = 'STUDENT'
}

export interface Gradjanin {
  id: number;
  ime: string;
  prezime: string;
  jmbg: string | null;
  radniStatus: RadniStatus;
  authGradjaninId: number;
  oblastZainteresovanosti: string;
}
