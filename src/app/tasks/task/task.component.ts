import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DatePipe } from "@angular/common";

import { task } from './task.model'
import { CardComponent } from "../../shared/card/card.component";

@Component({
    selector: 'app-task',
    standalone: true,
    templateUrl: './task.component.html',
    styleUrl: './task.component.css',
    imports: [CardComponent,DatePipe]
})
export class TaskComponent {
  @Input({required:true}) taskData!:task;

  @Output() complete = new EventEmitter<string>();


  onCompleteTask() {
    this.complete.emit(this.taskData.id)
    }



}
