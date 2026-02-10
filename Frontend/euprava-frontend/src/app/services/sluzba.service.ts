import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DiplomiraniStudent} from '../model/diplomirani-student';

@Injectable({ providedIn: 'root' })
export class SluzbaService {

  private baseUrl = 'http://localhost:8082/api/sluzba';

  constructor(private http: HttpClient) {}

  private authHeaders() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
  }

  getKandidati(): Observable<DiplomiraniStudent[]> {
    return this.http.get<DiplomiraniStudent[]>(
      `${this.baseUrl}/diplomirani`,
      this.authHeaders()
    );
  }
}

