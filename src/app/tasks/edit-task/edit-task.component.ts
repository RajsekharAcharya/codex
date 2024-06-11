import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { TasksService } from '../tasks.service';
import { task } from '../task/task.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-task',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './edit-task.component.html',
  styleUrl: './edit-task.component.css',
})
export class EditTaskComponent {
  @Input({ required: true }) taskId!: string;
  @Output() close = new EventEmitter<void>();

  private taskService = inject(TasksService);

  editTaskData!:task | undefined;

  ngOnInit() {
    this.editTaskData = this.taskService.getTaskById(this.taskId);
  }

  onClose() {
    this.close.emit();
  }

  onSubmit() {
    if(this.editTaskData){
      this.taskService.updateTask(this.editTaskData);
    }
    this.close.emit();
  }
}
