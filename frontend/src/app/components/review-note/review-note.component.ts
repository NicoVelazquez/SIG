import {Component, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-review-note',
  templateUrl: './review-note.component.html',
  styleUrls: ['./review-note.component.css']
})
export class ReviewNoteComponent implements OnInit {

  subscription: Subscription;
  note: any;
  application: any;
  headers = ['Nombre', 'Fecha', 'Lote', 'Cantidad', '# Acceptadas'];
  userType = 'manager';


  constructor(private rs: HttpRequestsService, private aRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.subscription = this.aRoute.params.subscribe(params => {
      this.rs.getNote(params.id).then(data => {
        this.note = data;
      });
      this.rs.getApplication(params.id).then(data => {
        this.application = data;
      });
    });

    this.aRoute.queryParams.subscribe(params => {
      if (params.user) {
        this.userType = params.user;
      }
    });
  }

}
