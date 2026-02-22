import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {forkJoin, of} from 'rxjs';

import { catchError } from 'rxjs/operators';
import { FakultetService, DiplomiranjeStatusDto, StudentMeDto } from '../../services/fakultet.service';

@Component({
  selector: 'app-diplomiranje',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './diplomiranje.component.html',
  styleUrl: './diplomiranje.component.css'
})
export class DiplomiranjeComponent implements OnInit {
  loading = false;
  error: string | null = null;

  data: DiplomiranjeStatusDto | null = null;
  student: StudentMeDto | null = null;

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitaj();
  }

  ucitaj() {
    this.loading = true;
    this.error = null;

    forkJoin({
      status: this.fakultetService.getDiplomiranjeStatus().pipe(
        catchError(() => of(null))
      ),
      me: this.fakultetService.me().pipe(
        catchError(() => of(null))
      )
    }).subscribe(({ status, me }) => {
      this.data = status;
      this.student = me;


      if (!status && !me) {
        this.error = 'Greška pri učitavanju statusa diplomiranja.';
      }

      this.loading = false;
    });
  }

  get ispunjava(): boolean {
    return this.data?.status === 'ISPUNJAVA';
  }

  get espbOk(): boolean {
    if (!this.data) return false;
    return (this.data.ukupnoEspb ?? 0) >= (this.data.potrebnoEspb ?? 240);
  }


  get diplomirao(): boolean {
    return this.student?.statusStudenta === 'DIPLOMIRAO';
  }
}
