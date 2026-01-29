export interface PrikazPrijave {
  oglasId: number;
  prijavaId: number;
  nazivPozicije: string;
  nazivKompanije: string;
  datumPrijave: string;
  status: 'PODNETA' | 'PRIHVACENA' | 'ODBIJENA';
  razlogOdbijanja?: string;
}


export interface PrikazPrijavePoslodavacDTO {
  idPrijave: number;
  idOglasa: number;
  nazivPozicije: string;
  nazivKompanije: string;
  datumPrijave: string;
  status: 'PODNETA' | 'PRIHVACENA' | 'ODBIJENA';
  razlogOdbijanja?: string;

  imeGradjanina: string;
  prezimeGradjanina: string;
  oblastGradjanina?: string;
  radniStatusGradjanina?: string;
}


