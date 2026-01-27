import { Component, OnInit } from '@angular/core';
import { GradjaninService } from '../../services/gradjanin.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {Gradjanin, RadniStatus} from '../../model/gradjanin.model';

@Component({
  selector: 'app-profil-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profil-edit.component.html',
  styleUrls: ['./profil-edit.component.css']
})
export class ProfilEditComponent implements OnInit {

  gradjanin: Gradjanin = {} as Gradjanin;
  loading = true;

  RadniStatus = RadniStatus;

  constructor(
    private gradjaninService: GradjaninService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.gradjaninService.getMe().subscribe(data => {
      this.gradjanin = data;
      this.loading = false;
    });
  }

  save() {
    this.gradjaninService.updateMe(this.gradjanin).subscribe({
      next: () => this.router.navigate(['/profil']),
      error: err => console.error(err)
    });
  }
}
