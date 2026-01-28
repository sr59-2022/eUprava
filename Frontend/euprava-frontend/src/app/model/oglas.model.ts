export interface Oglas {
  id: number;
  nazivPozicije: string;
  opis: string;
  datumObjave: string;
  rokPrijave: string;
  tipOglasa: string;
  nazivKompanije: string;
  oblast?: string;
}

export enum TipOglasa {
  PRAKSA = 'PRAKSA',
  POSAO = 'POSAO'
}
