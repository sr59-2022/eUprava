import { TipOglasa } from './oglas.model';

export interface OglasRequest {
  nazivPozicije: string;
  opis: string;
  nazivKompanije: string;
  rokPrijave: string;
  tipOglasa: TipOglasa;
}
