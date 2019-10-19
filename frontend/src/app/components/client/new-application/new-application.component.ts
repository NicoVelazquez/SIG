import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpRequestsService} from '../../../shared/services/http-requests.service';

@Component({
  selector: 'app-new-application',
  templateUrl: './new-application.component.html',
  styleUrls: ['./new-application.component.css']
})
export class NewApplicationComponent implements OnInit {

  applicationForm: FormGroup;
  headers = ['Nombre', 'Fecha', 'Lote', 'Cantidad'];
  products = [];

  selectedProducts = [];
  selectedProduct: any;
  selectedQuantity = 0;

  moreProduct = true;

  constructor(private fb: FormBuilder, private rs: HttpRequestsService) {
    this.applicationForm = fb.group({
      date: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required])
    });
  }

  ngOnInit() {
    this.applicationForm.patchValue({
      date: this.parseDate(new Date(Date.now()))
    });

    this.rs.getClientProducts(1).then(data => this.products = data);
  }

  parseDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return year + '-' + month + '-' + day;
  }

  selectProduct($event: any) {
    const index = this.products.findIndex(e => e.name === $event.target.value);
    this.selectedProduct = this.products.splice(index, 1)[0];
  }

  addProduct() {
    this.moreProduct = false;
    this.selectedProduct.quantity = this.selectedQuantity;
    this.selectedProducts.push(this.selectedProduct);

    this.selectedProduct = null;
    this.selectedQuantity = 0;
  }

  isValidApplication() {
    return this.applicationForm.valid && this.selectedProducts.length > 0;
  }

  async createApplication(): Promise<any> {
    const newApplication = this.applicationForm.value;
    newApplication.produts = this.selectedProducts;
    newApplication.state = 'Enviada';
    await this.rs.createClientApplication(1, newApplication)
      .then(newId => newApplication.id = newId)
      .catch(err => {
        console.log('Error en creacion de aplicacion');
      });
    return newApplication;
  }
}
