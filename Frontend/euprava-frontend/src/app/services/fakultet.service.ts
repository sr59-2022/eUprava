import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface OcenaPregledDto {
  ocenaId: number;
  vrednost: number | null;
  datumUpisa: string;

  ispitId: number;
  datumOdrzavanja: string;

  predmetId: number;
  predmetSifra: string;
  predmetNaziv: string;
  predmetEspb: number;

  rokNaziv: string;
}

export interface UverenjeDto {
  id: number;
  brojDokumenta: string;
  datumIzdavanja: string;
  tip: string;
}

export interface OceneFilter {
  ocena?: number | null;
  ocenaMin?: number | null;
  ocenaMax?: number | null;
  polozio?: boolean | null;
  predmet?: string | null;
}

export interface DiplomiranjeStatusDto {
  status: 'ISPUNJAVA' | 'NE_ISPUNJAVA';
  ukupnoEspb: number;
  potrebnoEspb: number;
  zavrsniRadOdbranjen: boolean;
}

export interface DiplomiraniPoGodiniDto {
  godina: number;
  brojDiplomiranih: number;
}

export interface IspitOpcijaDto {
  id: number;
  predmetNaziv: string;
  rokNaziv: string;
  datumOdrzavanja: string;
  prijavaDo: string;
}

export interface PrijavaIspitaDto {
  id: number;
  ispitId: number;
  predmetNaziv: string;
  rokNaziv: string;
  datumOdrzavanja: string;
  prijavaDo: string;
  status: string;
  datumPrijave: string;
}


export interface IspitniRokDto {
  id: number;
  naziv: string;
  pocetak: string;
  kraj: string;
}

export interface IspitniRokCreateDto {
  naziv: string;
  pocetak: string;
  kraj: string;
}

export interface PredmetDto {
  id: number;
  sifra: string;
  naziv: string;
  espb: number;
}

export interface IspitCreateDto {
  predmetId: number;
  rokId: number;
  datumOdrzavanja: string;
  prijavaDo: string;
  sala: string;
}

export interface StudentRowDto {
  id: number;
  brojIndeksa: string;
  ime: string;
  prezime: string;
  statusStudenta: string;
  zavrsniRadOdbranjen: boolean;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface PredmetIndeksDto {
  predmetId: number;
  sifra: string;
  naziv: string;
  espb: number;
  ocena: number | null;
  polozio: boolean;
}

export interface PredmetiFilter {
  polozio?: boolean | null;
  predmet?: string | null;
}

@Injectable({ providedIn: 'root' })
export class FakultetService {
  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {}

  private authOptions() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token ?? ''}`
      })
    };
  }


  dostupniIspiti(): Observable<IspitOpcijaDto[]> {
    return this.http.get<IspitOpcijaDto[]>(
      `${this.baseUrl}/api/ispiti/dostupni`,
      this.authOptions()
    );
  }

  prijaviIspit(ispitId: number) {
    return this.http.post(
      `${this.baseUrl}/api/ispiti/${ispitId}/prijava`,
      {},
      this.authOptions()
    );
  }

  otkaziPrijavu(ispitId: number) {
    return this.http.post(
      `${this.baseUrl}/api/ispiti/${ispitId}/otkazi`,
      {},
      this.authOptions()
    );
  }

  mojePrijave(): Observable<PrijavaIspitaDto[]> {
    return this.http.get<PrijavaIspitaDto[]>(
      `${this.baseUrl}/api/ispiti/moje-prijave`,
      this.authOptions()
    );
  }


  me() {
    return this.http.get(
      `${this.baseUrl}/api/fakultet/me`,
      this.authOptions()
    );
  }

  mojeOcene(filters?: OceneFilter): Observable<OcenaPregledDto[]> {
    let params = new HttpParams();

    if (filters) {
      if (filters.ocena != null) params = params.set('ocena', String(filters.ocena));
      if (filters.ocenaMin != null) params = params.set('ocenaMin', String(filters.ocenaMin));
      if (filters.ocenaMax != null) params = params.set('ocenaMax', String(filters.ocenaMax));
      if (filters.polozio != null) params = params.set('polozio', String(filters.polozio));
      if (filters.predmet != null && filters.predmet.trim().length > 0) {
        params = params.set('predmet', filters.predmet.trim());
      }
    }

    return this.http.get<OcenaPregledDto[]>(
      `${this.baseUrl}/api/fakultet/ocene/me`,
      {
        ...this.authOptions(),
        params
      }
    );
  }

  mojiPredmetiIndeks(filters?: PredmetiFilter): Observable<PredmetIndeksDto[]> {
    let params = new HttpParams();

    if (filters) {
      if (filters.polozio != null) params = params.set('polozio', String(filters.polozio));
      if (filters.predmet != null && filters.predmet.trim().length > 0) {
        params = params.set('predmet', filters.predmet.trim());
      }
    }

    return this.http.get<PredmetIndeksDto[]>(
      `${this.baseUrl}/api/predmeti/indeks/me`,
      { ...this.authOptions(), params }
    );
  }
  izdajUverenje(tip: string): Observable<UverenjeDto> {
    return this.http.post<UverenjeDto>(
      `${this.baseUrl}/api/fakultet/uverenja/me?tip=${encodeURIComponent(tip)}`,
      {},
      this.authOptions()
    );
  }

  mojaUverenja(): Observable<UverenjeDto[]> {
    return this.http.get<UverenjeDto[]>(
      `${this.baseUrl}/api/fakultet/uverenja/me`,
      this.authOptions()
    );
  }

  preuzmiUverenjePdf(id: number): Observable<Blob> {
    return this.http.get(
      `${this.baseUrl}/api/fakultet/uverenja/${id}/pdf`,
      {
        ...this.authOptions(),
        responseType: 'blob' as const
      }
    );
  }


  getDiplomiranjeStatus(): Observable<DiplomiranjeStatusDto> {
    return this.http.get<DiplomiranjeStatusDto>(
      `${this.baseUrl}/api/fakultet/me/diplomiranje-status`,
      this.authOptions()
    );
  }

  getDiplomiraniPoGodini(): Observable<DiplomiraniPoGodiniDto[]> {
    return this.http.get<DiplomiraniPoGodiniDto[]>(
      `${this.baseUrl}/api/fakultet/izvestaji/diplomirani-po-godini`,
      this.authOptions()
    );
  }


  listaIspitaZaProfesora(): Observable<IspitOpcijaDto[]> {
    return this.http.get<IspitOpcijaDto[]>(
      `${this.baseUrl}/api/ispiti/lista`,
      this.authOptions()
    );
  }

  prijaveZaIspit(ispitId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.baseUrl}/api/ispiti/${ispitId}/prijave`,
      this.authOptions()
    );
  }

  upisiOcenu(studentId: number, ispitId: number, vrednost: number) {
    return this.http.post(
      `${this.baseUrl}/api/fakultet/ocene`,
      { studentId, ispitId, vrednost },
      this.authOptions()
    );
  }



  rokovi(): Observable<IspitniRokDto[]> {
    return this.http.get<IspitniRokDto[]>(
      `${this.baseUrl}/api/rokovi`,
      this.authOptions()
    );
  }

  kreirajRok(dto: IspitniRokCreateDto): Observable<IspitniRokDto> {
    return this.http.post<IspitniRokDto>(
      `${this.baseUrl}/api/rokovi`,
      dto,
      this.authOptions()
    );
  }


  predmeti(): Observable<PredmetDto[]> {
    return this.http.get<PredmetDto[]>(
      `${this.baseUrl}/api/predmeti`,
      this.authOptions()
    );
  }


  kreirajIspit(dto: IspitCreateDto): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/api/ispiti-admin`,
      dto,
      this.authOptions()
    );
  }

  getStudenti(q = '', page = 0, size = 20): Observable<PageResponse<StudentRowDto>> {
    let params = new HttpParams()
      .set('page', String(page))
      .set('size', String(size));

    if (q && q.trim().length > 0) {
      params = params.set('q', q.trim());
    }

    return this.http.get<PageResponse<StudentRowDto>>(
      `${this.baseUrl}/api/fakultet/studenti`,
      { ...this.authOptions(), params }
    );
  }

  postaviZavrsniRad(studentId: number, odbranjen: boolean) {
    return this.http.patch<void>(
      `${this.baseUrl}/api/fakultet/studenti/${studentId}/zavrsni-rad`,
      { odbranjen },
      this.authOptions()
    );
  }
}
