import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AnswerComponent } from './paginas/answer/answer.component';
import { QuestionComponent } from './paginas/question/question.component';
import { RequestionComponent } from './paginas/requestion/requestion.component';
import { LoginComponent } from './persona/login/login.component';
import { QuestionsComponent } from './persona/preguntas/preguntas.component';
import { RecoverPasswordComponent } from './persona/recover-password/recover-password.component';
import { RegisterComponent } from './persona/registro/registro.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'preguntas', component: QuestionsComponent},
  { path: 'registro', component: RegisterComponent},
  { path: 'answer', component: AnswerComponent},
  {path: 'question/:id', component: RequestionComponent},
  {path: 'recuperar-contraseña', component: RecoverPasswordComponent},
  {path: '**', pathMatch: 'full', redirectTo:'preguntas'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }



