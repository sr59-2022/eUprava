import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Oglas, TipOglasa} from '../model/oglas.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OglasService {

  private apiUrl = 'http://localhost:8082/api/oglasi';

  constructor(private http: HttpClient) { }

  getAllOglasi() {
    const token = localStorage.getItem('token');

    return this.http.get<Oglas[]>(this.apiUrl, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  pretragaOglasa(nazivPozicije?: string, tipOglasa?: TipOglasa): Observable<Oglas[]> {
    const token = localStorage.getItem('token');
    let params = new HttpParams();

    if (nazivPozicije) params = params.set('naziv', nazivPozicije);
    if (tipOglasa) params = params.set('tip', tipOglasa);

    return this.http.get<Oglas[]>(`${this.apiUrl}/pretraga`, {
      headers: { Authorization: `Bearer ${token}` },
      params
    });
  }

}

