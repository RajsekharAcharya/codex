import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { DatePipe } from '@angular/common';

import { task } from './task.model';
import { CardComponent } from '../../shared/card/card.component';
import { TasksService } from '../tasks.service';
import { EditTaskComponent } from '../edit-task/edit-task.component';

@Component({
  selector: 'app-task',
  standalone: true,
  templateUrl: './task.component.html',
  styleUrl: './task.component.css',
  imports: [CardComponent, DatePipe, EditTaskComponent],
})
export class TaskComponent {
  @Input({ required: true }) taskData!: task;
  private taskService = inject(TasksService);

  isEditingTask = false;

  onCompleteTask() {
    this.taskService.removeTask(this.taskData.id);
  }

  onEditTask() {
    this.isEditingTask = true;
  }

  onCloseEditTask() {
    this.isEditingTask = false;
  }
}
