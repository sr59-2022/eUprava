export interface PrikazPrijave {
  oglasId: number;
  prijavaId: number;
  nazivPozicije: string;
  nazivKompanije: string;
  datumPrijave: string;
  status: 'PODNETA' | 'PRIHVACENA' | 'ODBIJENA';
  razlogOdbijanja?: string;
}

