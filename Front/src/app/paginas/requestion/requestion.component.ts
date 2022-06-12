import { DOCUMENT } from '@angular/common';
import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';

@Component({
  selector: 'app-requestion',
  templateUrl: './requestion.component.html',
  styleUrls: ['./requestion.component.css']
})
export class RequestionComponent implements OnInit {
  
  question:QuestionI | undefined;
  answers: AnswerI[] = []
  answersNew: AnswerI[]=[];
  currentAnswer:number=0;

  questions: QuestionI[] | undefined;
 
  page: number = 0;

  showButton = false
  scrollHeight = 300

  constructor(@Inject(DOCUMENT) private document: Document,
    private route:ActivatedRoute,
    private questionService:QuestionService,
    private service: QuestionService, 
    ) {}

  id:string | undefined;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.getAnswers(id as string);
    this.get2();
    
  }
  
  get2(){
    let id = this.route.snapshot.paramMap.get('id');
    

    this.service.getQuestion(id).subscribe((data) => {  
          this.question = data;
    });
  }

  getAnswers(id:string):void{
    this.questionService.getAnswers(id).subscribe((data : AnswerI[])=>{
      this.answers = data;
    })

  }

  AddAnwsers(index:number) {
    let last=this.currentAnswer+index;
    for(let i = this.currentAnswer;i<last;i++){
    }
    this.currentAnswer+=10;
  }

  onScroll() {

  }

  @HostListener('window:scroll')
  onWindowScroll() : void{
    const yOffSet = window.pageYOffset
    const scrollTop = this.document.documentElement.scrollTop
    this.showButton = (yOffSet || scrollTop) > this.scrollHeight
  }

  onScrollTop() : void{
    this.document.documentElement.scrollTop = 0
  }

}
