import { DOCUMENT } from '@angular/common';
import { Component, HostListener, Input, OnInit, Inject } from '@angular/core';
import { inject } from '@angular/core/testing';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-questions',
  templateUrl: './preguntas.component.html',
  styleUrls: ['./preguntas.component.css'],
})
export class QuestionsComponent implements OnInit {
  userLogged = this.authService.getUserLogged();
  uid: any;
  showButton = false
  scrollHeight = 100
  
  @HostListener('window:scroll')
  onWindowScroll():void{
    const yOffSet = window.pageYOffset
    const scrollTop = this.document.documentElement.scrollTop
    this.showButton = (yOffSet || scrollTop) > this.scrollHeight
  }

  onScrollTop(): void{
    this.document.documentElement.scrollTop = 0
  }

  onScrollDown():void{
    console.log('mamarre')
  }
  
  totalQuestions: number = 0;
  
  questions: QuestionI[] | undefined;
  user: any = '';
  page: number = 0;
  pages: Array<number> | undefined;
  disabled: boolean = false;
  
  constructor(
    @Inject(DOCUMENT) private document: Document,
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
    this.service.getPage(this.page).subscribe((data) => {
        this.questions = data;
    });
    this.service
      .getTotalPages()
      .subscribe((data) => (this.pages = new Array(data)));
    this.service
      .getCountQuestions()
      .subscribe((data) => (this.totalQuestions = data));
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
