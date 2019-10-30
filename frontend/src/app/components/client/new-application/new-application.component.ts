import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpRequestsService} from '../../../shared/services/http-requests.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-application',
  templateUrl: './new-application.component.html',
  styleUrls: ['./new-application.component.css']
})
export class NewApplicationComponent implements OnInit {

  applicationForm: FormGroup;
  headers = ['Nombre', 'Lote', 'Fecha de expiración', 'Cantidad', 'Razón', ''];
  products = [];

  selectedProducts = [];
  selectedProduct: any;
  selectedQuantity = 0;

  moreProduct = true;

  reasons = ['Rechazo por no pedido', 'Rechazo por rotura', 'Devolucion por vencido', 'Devolución por rotura'];
  selectedReason = 'Rechazo por no pedido';

  constructor(private fb: FormBuilder, private rs: HttpRequestsService, private router: Router) {
    this.applicationForm = fb.group({
      date: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required])
    });
  }

  ngOnInit() {
    this.applicationForm.patchValue({
      date: this.parseDate(new Date(Date.now()))
    });
    this.rs.getClientProducts().then(data => {
      this.products = data;
    });
  }

  parseDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return year + '-' + month + '-' + day;
  }

  selectProduct($event: any) {
    this.selectedProduct = this.products.filter(e => e.id === +$event.target.value)[0];
  }

  selectType($event: any) {
    this.selectedReason = $event.target.value;
  }

  addProduct() {
    this.moreProduct = false;
    this.selectedProduct.selectedQuantity = this.selectedQuantity;
    this.selectedProduct.selectedType = this.selectedReason;
    this.selectedProducts.push(this.selectedProduct);
    const index = this.products.findIndex(e => e.id === this.selectedProduct.id);
    this.products.splice(index, 1);

    this.selectedReason = 'Rechazo por no pedido';
    this.selectedProduct = null;
    this.selectedQuantity = 0;
  }

  removeProduct(p: any) {
    const index = this.selectedProducts.findIndex(e => e.id === p.id);
    this.selectedProducts.splice(index, 1);
    delete p.selectedQuantity;
    delete p.selectedType;
    this.products.push(p);
  }

  isValidApplication() {
    return this.applicationForm.valid && this.selectedProducts.length > 0;
  }

  createApplication() {
    const newApplication = this.applicationForm.value;

    newApplication.products = [];
    this.selectedProducts.forEach(e => {
      newApplication.products.push({productId: e.id, quantity: e.selectedQuantity, reason: e.selectedType});
    });
    console.log(newApplication);

    // TODO (NV) - descomentar cuando curi arregle el createApplication
    this.rs.createClientApplication(newApplication).then(() => {
      this.router.navigate(['home']);
    }).catch(err => {
      console.log('Error en creacion de aplicacion');
      console.log(err);
    });
  }
}
