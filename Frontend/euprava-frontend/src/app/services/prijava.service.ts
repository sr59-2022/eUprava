import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PrikazPrijave} from '../model/prijava.model';

@Injectable({
  providedIn: 'root'
})
export class PrijavaService {
  private apiUrl = 'http://localhost:8082/api/prijave';

  constructor(private http: HttpClient) {
  }

  prijaviSe(oglasId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/prijavi`, null, { params: { oglasId } });
  }

  getMojePrijave(): Observable<PrikazPrijave[]> {
    return this.http.get<PrikazPrijave[]>(`${this.apiUrl}/moje`);
  }
}
