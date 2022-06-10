import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/Service/service.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css'],
  providers: [ServiceService],
})
export class RecoverPasswordComponent implements OnInit {
  emailUser = new FormControl('');
  constructor(
    public service: ServiceService,
  ) {}

  ngOnInit() {}
  }

