import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
}
