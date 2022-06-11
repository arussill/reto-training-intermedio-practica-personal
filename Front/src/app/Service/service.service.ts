import { Injectable, NgZone } from '@angular/core';
import { MessageService } from 'primeng/api';

import { AngularFireAuth } from '@angular/fire/compat/auth';
import {
  AngularFirestore,
  AngularFirestoreDocument,
} from '@angular/fire/compat/firestore';
import { Router } from '@angular/router';

import firebase from 'firebase/compat/app';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {
  userData: any;
  mostrar: Boolean = false;
  constructor(
    public afauth: AngularFireAuth,
    public store: AngularFirestore,
    public router: Router,
    public messageService: MessageService,
    public ngZone: NgZone
  ) {
    this.afauth.authState.subscribe((user) => {
      if (user) {
        this.userData = user;
        JSON.parse(localStorage.getItem('user')!);
        localStorage.setItem('user', JSON.stringify(this.userData));
      } else {
        JSON.parse(localStorage.getItem('user')!);
        localStorage.setItem('user', 'null');
      }
    });
  }

  async login(email: string, password: string) {
    try {
      return await await this.afauth.signInWithEmailAndPassword(
        email,
        password
      );
    } catch (error) {
      return null;
    }
  }

  async loginRegistre(email: string, password: string) {
    try {
      const registro = await this.afauth.createUserWithEmailAndPassword(
        email,
        password
      );

      this.afauth.currentUser.then((user) => {
        user?.sendEmailVerification();
      });

      return registro;
    } catch (error) {
      return null;
    }
  }

  resetPassword(email: string) {
    return this.afauth
      .sendPasswordResetEmail(email)
      .then(() => {
        window.confirm(
          'Se ha enviado un correo para restablecer la contraseña'
        );
      })
      .catch(() => {
        window.alert('Error al enviar el correo, intente de nuevo');
      });
  }

  // loginGoogle() {
  //   return this.afauth
  //     .signInWithPopup(new firebase.auth.GoogleAuthProvider())
  //     .then((res) => {
  //       if (res) {
  //         this.router.navigate(['preguntas']);
  //       }
  //     })
  //     .catch((error) => {
  //       return null;
  //     });
  // }

  // async loginGoogle() {
  //   try {
  //     return await this.afauth.signInWithPopup(
  //       new firebase.auth.GoogleAuthProvider()
  //     );
  //   } catch (error) {
  //     return null;
  //   }
  // }

  loginGoogle() {
    return this.AuthLogin(new firebase.auth.GoogleAuthProvider()).then((res: any) => {
      if (res) {
        this.router.navigate(['preguntas']);
      }
    });
  }

  AuthLogin(provider: any) {
    return this.afauth
      .signInWithPopup(provider)
      .then((result) => {
        this.ngZone.run(() => {
          this.router.navigate(['preguntas']);
        });
        this.SetUserData(result.user);
      })
      .catch((error) => {
        window.alert(error);
      });
  }

  getUserLogged() {
    return this.afauth.authState;
  }

  logout() {
    return this.afauth.signOut().then(() => {
      console.log(localStorage.getItem('user'));
      localStorage.removeItem('user');
      console.log(localStorage.getItem('user'));
      this.router.navigate(['login']);
    });}

  SetUserData(user: any) {
    const userRef: AngularFirestoreDocument<any> = this.store.doc(
      `users/${user.uid}`
    );
    const userData: User = {
      uid: user.uid,
      email: user.email,
      displayName: user.displayName,
      photoURL: user.photoURL,
      emailVerified: user.emailVerified,
    };
    return userRef.set(userData, {
      merge: true,
    });
  }
}
