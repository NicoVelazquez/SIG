import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {HttpRequestsService} from '../../../shared/services/http-requests.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-create-note',
  templateUrl: './create-note.component.html',
  styleUrls: ['./create-note.component.css']
})
export class CreateNoteComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  noteForm: FormGroup;
  application: any;
  headers = ['Nombre', 'Fecha', 'Lote', 'Cantidad', 'Estado'];

  constructor(private fb: FormBuilder, private rs: HttpRequestsService, private router: Router, private aRoute: ActivatedRoute) {
    this.noteForm = fb.group({
      date: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      price: new FormControl(null, [Validators.required])
    });
  }

  ngOnInit() {
    this.subscription = this.aRoute.params.subscribe(params => {
      this.rs.getManagerApplication(params.id).then(data => {
        this.application = data;
        console.log(this.application);
      });
    });

    this.noteForm.patchValue({
      date: this.parseDate(new Date(Date.now()))
    });
  }

  parseDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return year + '-' + month + '-' + day;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
