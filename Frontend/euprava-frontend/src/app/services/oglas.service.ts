import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Oglas, TipOglasa } from '../model/oglas.model';
import {OglasRequest} from '../model/oglas-request.model';

@Injectable({
  providedIn: 'root'
})
export class OglasService {

  private apiUrl = 'http://localhost:8082/api/oglasi';

  constructor(private http: HttpClient) {}

  getAllOglasi(): Observable<Oglas[]> {
    return this.http.get<Oglas[]>(this.apiUrl);
  }

  pretragaOglasa(
    nazivPozicije?: string,
    tipOglasa?: TipOglasa
  ): Observable<Oglas[]> {

    let params = new HttpParams();

    if (nazivPozicije) params = params.set('naziv', nazivPozicije);
    if (tipOglasa) params = params.set('tip', tipOglasa);

    return this.http.get<Oglas[]>(`${this.apiUrl}/pretraga`, { params });
  }

  dodajOglas(oglas: OglasRequest) {
    const token = localStorage.getItem('token');
    return this.http.post(`${this.apiUrl}/dodaj`, oglas, {
      headers: { Authorization: `Bearer ${token}` }
    });
  }

  getPreporuke(): Observable<Oglas[]> {
    return this.http.get<Oglas[]>(`${this.apiUrl}/preporuke`);
  }

  getMojiOglasi(): Observable<Oglas[]> {
    const token = localStorage.getItem('token');
    return this.http.get<Oglas[]>(`${this.apiUrl}/moji`, {
      headers: { Authorization: `Bearer ${token}` }
    });
  }

  getOglasiZaDiplomirane(): Observable<Oglas[]> {

    return this.http.get<Oglas[]>('http://localhost:8081/api/fakultet/oglasi');
  }

  prijaviNaOglas(oglasId: number) {
    const token = localStorage.getItem('token');
    return this.http.post(
      `http://localhost:8081/api/fakultet/oglasi/${oglasId}/prijavi`,
      {},
      { headers: { Authorization: `Bearer ${token}` } }
    );
  }
}
