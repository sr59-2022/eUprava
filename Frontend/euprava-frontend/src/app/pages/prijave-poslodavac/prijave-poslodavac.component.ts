import {Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy} from '@angular/core';
import {PrikazPrijavePoslodavacDTO} from '../../model/prijava.model';
import {PrijavaService} from '../../services/prijava.service';
import {CommonModule, DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-prijave-poslodavac',
  standalone: true,
  imports: [
    DatePipe, CommonModule, FormsModule
  ],
  templateUrl: './prijave-poslodavac.component.html',
  styleUrl: './prijave-poslodavac.component.css',
  changeDetection: ChangeDetectionStrategy.Default
})
export class PrijavePoslodavacComponent implements OnInit {

  prijave: PrikazPrijavePoslodavacDTO[] = [];
  loading = false;
  odbijModalOpen = false;
  odbijPrijavaId?: number;
  odbijRazlog: string = '';


  constructor(private service: PrijavaService,  private cd: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.ucitajPrijave();
  }

  ucitajPrijave(): void {
    this.loading = true;
    this.service.getPrijavePoslodavca().subscribe({
      next: data => this.prijave = data,
      error: err => console.error(err),
      complete: () => this.loading = false
    });
  }


  prihvati(prijavaId: number): void {
    this.service.prihvatiPrijavu(prijavaId).subscribe({
      next: () => {
        this.ucitajPrijave();
      },
      error: err => console.error(err)
    });
  }

  odbij(prijavaId: number): void {
    const razlog = prompt('Unesite razlog odbijanja:');
    if (!razlog) return;

    this.service.odbijPrijavu(prijavaId, razlog).subscribe({
      next: () => {
        this.ucitajPrijave();
      },
      error: err => console.error(err)
    });
  }

  otvoriOdbijModal(prijavaId: number) {
    this.odbijPrijavaId = prijavaId;
    this.odbijRazlog = '';
    this.odbijModalOpen = true;
  }

  potvrdiOdbij() {
    if (!this.odbijPrijavaId || !this.odbijRazlog.trim()) return;

    this.service.odbijPrijavu(this.odbijPrijavaId, this.odbijRazlog).subscribe({
      next: () => {
        this.ucitajPrijave();
        this.odbijModalOpen = false;
      },
      error: err => console.error(err)
    });
  }

  zatvoriOdbijModal() {
    this.odbijModalOpen = false;
  }



}
