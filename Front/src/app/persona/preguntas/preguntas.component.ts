import { Component, Input, OnInit } from '@angular/core';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-preguntas',
  templateUrl: './preguntas.component.html',
  styleUrls: ['./preguntas.component.css'],
})
export class QuestionsComponent implements OnInit {
  userLogged = this.authService.getUserLogged();
  uid: any;

  totalQuestions: number = 0;

  questions: QuestionI[] | undefined;
  user: any = '';
  page: number = 0;
  pages: Array<number> | undefined;
  disabled: boolean = false;

  constructor(
    private service: QuestionService,
    public authService: ServiceService
  ) {}

  ngOnInit(): void {
    this.getQuestions();
    this.traerdatos();
  }

  getQuestions(): void {
    this.userLogged.subscribe(value =>{
        this.uid=value?.uid
      });
      this.service.getAllQuestions().subscribe(
        (data) => {
          console.log(data);
          this.questions = data;
        }
      )
  }

  isLast(): boolean {
    let totalPeges: any = this.pages?.length;
    return this.page == totalPeges - 1;
  }

  isFirst(): boolean {
    return this.page == 0;
  }

  previousPage(): void {
    !this.isFirst() ? (this.page--, this.getQuestions()) : false;
  }

  nextPage(): void {
    !this.isLast() ? (this.page++, this.getQuestions()) : false;
  }

  getPage(page: number): void {
    this.page = page;
    console.log(this.page);
    this.getQuestions();
  }

  traerdatos() {
    this.userLogged.subscribe((value) => {
      if (value?.email == undefined) {
        this.disabled = true;
      } else {
        this.disabled = false;
      }
    });
  }
}