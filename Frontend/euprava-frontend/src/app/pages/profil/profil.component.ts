import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Gradjanin, GradjaninService} from '../../services/gradjanin.service';
import {Router, RouterModule} from '@angular/router';


@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  gradjanin?: Gradjanin;
  loading = true;


  constructor(private gradjaninService: GradjaninService, private router: Router) {

  }

  ngOnInit(): void {
    this.gradjaninService.getMe().subscribe({
      next: data => {
        this.gradjanin = data;
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.loading = false;
      }
    });

  }
  onEdit() {
    this.router.navigate(['/profil/uredi']);
  }
}
