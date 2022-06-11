import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, Message } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss'],
  providers: [MessageService],
})
export class RegisterComponent implements OnInit {
  mostrar: Boolean = false;
  val1: number = 3;

  public form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    rating: ['', []],
  });

  constructor(
    public formBuilder: FormBuilder,
    public messageService: MessageService,
    public authService: ServiceService,
    public route: Router
  ) {}

  ngOnInit(): void {}

  ingresar() {
    this.mostrar = !this.mostrar;

    this.authService
      .loginRegistre(this.form.value.email, this.form.value.password)
        .then((res) => {
        if (res) {
          this.messageService.add({
            severity: 'success',
            summary: '!Exitoso¡',
            detail: 'Usuario Almacenado correctamente',
          });this.route.navigate(['preguntas']);
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Usuarios Registrado',
            detail: 'Por favor intente con otro correo',
          });
        }
        this.mostrar = !this.mostrar;
      });
  }
  ingresarGoogle() {
    this.mostrar = !this.mostrar;
    this.authService
      .loginGoogle()
      .then(() => {
        this.mostrar = !this.mostrar;
      });
  }
  getUserLogged() {
    this.authService.getUserLogged().subscribe((res) => {
      return res;
    });
  }

  questionsHome() {
    this.route.navigate(['preguntas']);
  }

  //TODO: Utilidades
  showSuccess() {
    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Message Content',
    });
  }
  showInfo() {
    this.messageService.add({
      severity: 'info',
      summary: 'Info',
      detail: 'Message Content',
    });
  }

  spinner() {
    this.mostrar = !this.mostrar;
  }
}
