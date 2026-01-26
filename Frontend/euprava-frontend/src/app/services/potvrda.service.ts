import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface GradjaninDTO {
  id: number;
  ime: string;
  prezime: string;
  radniStatus: string;
  imaPotvrdu: boolean;
}

@Injectable({ providedIn: 'root' })
export class PotvrdaService {

  private api = 'http://localhost:8082/api';

  constructor(private http: HttpClient) {}

  // Građanin zatraži potvrdu
  zatraziPotvrdu(gradjaninId: number): Observable<any> {
    return this.http.post(`${this.api}/gradjanin/zatrazi-potvrdu/${gradjaninId}`, {});
  }

  // Admin izda potvrdu
  izdajPotvrdu(gradjaninId: number): Observable<any> {
    return this.http.post(`${this.api}/admin/izdaj-potvrdu/${gradjaninId}`, {});
  }

  // Admin dobija listu građana sa informacijom da li imaju potvrdu
  getGradjani(): Observable<GradjaninDTO[]> {
    return this.http.get<GradjaninDTO[]>(`${this.api}/admin/gradjani`);
  }
}
