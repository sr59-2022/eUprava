import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface OcenaPregledDto {
  ocenaId: number;
  vrednost: number;
  datumUpisa: string;

  ispitId: number;
  datumOdrzavanja: string;

  predmetId: number;
  predmetSifra: string;
  predmetNaziv: string;
  predmetEspb: number;

  rokNaziv: string;
}

@Injectable({ providedIn: 'root' })
export class FakultetService {
  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {}

  private authOptions() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
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

  mojePrijave() {
    return this.http.get(
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


  mojeOcene(): Observable<OcenaPregledDto[]> {
    return this.http.get<OcenaPregledDto[]>(
      `${this.baseUrl}/api/fakultet/ocene/me`,
      this.authOptions()
    );
  }
}
