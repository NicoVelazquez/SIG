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
  headers = ['Nombre', 'Fecha', 'Lote', 'Cantidad', '# Aceptadas', '# Buenas'];
  canFinishNote = true;

  constructor(private fb: FormBuilder, private rs: HttpRequestsService, private router: Router, private aRoute: ActivatedRoute) {
    this.noteForm = fb.group({
      date: new FormControl(null, [Validators.required]),
      descriptionCredit: new FormControl(null, [Validators.required]),
      descriptionDebit: new FormControl(null, [Validators.required]),
      priceCredit: new FormControl(0, [Validators.required]),
      priceDebit: new FormControl(0, [Validators.required])
    });
  }

  ngOnInit() {
    this.subscription = this.aRoute.params.subscribe(params => {
      this.rs.getApplication(params.id).then(data => {
        this.application = data;
        this.updatePrices();
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

  validateAcceptedGoodQuantity($event: any, product: any) {
    this.canFinishNote = (product.quantity >= product.accepted && product.accepted >= product.good && product.good >= 0);
  }

  updatePrices() {
    let creditPrice = 0;
    let debitPrice = 0;
    this.application.products.forEach(e => {
      creditPrice += (e.accepted * e.price);
      // TODO (NV) - me falta multiplicar por el precio del operador
      debitPrice += ((e.quantity - e.accepted) * e.weight * this.application.cost);
    });

    this.noteForm.patchValue({
      priceCredit: creditPrice,
      priceDebit: debitPrice,
    });
  }

  createNote() {
    const note = this.noteForm.value;
    note.applicationId = this.application.id;
    console.log(note);
    this.rs.createManagerNote(note).then(() => {
      this.router.navigate(['home']);
    });
  }
}
