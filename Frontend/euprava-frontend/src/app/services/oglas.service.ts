import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Oglas } from '../model/oglas.model';

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

}

