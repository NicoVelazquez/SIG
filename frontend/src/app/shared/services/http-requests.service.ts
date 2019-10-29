import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {

  // TODO - setear url para pegarle al back
  url = 'http://localhost:9000';

  currentUser: any;

  constructor(private http: HttpClient) {
    this.currentUser = JSON.parse(localStorage.getItem('user'));
  }

  setCurrentUser(user: any) {
    this.currentUser = user;
  }

  signIn(email: string, password: string): Promise<any> {
    const credentials = {email, password};
    return this.http.post<any[]>(`${this.url}/login`, credentials).toPromise();

    // MOCK
    // return Promise.resolve({id: 1, role: 'client'});
  }

  getApplication(id: any): Promise<any> {
    return this.http.get<any[]>(`${this.url}/applications/${id}`).toPromise();
    //   return Promise.resolve(
    //     {
    //       id: '0', client: 'Velazquez', date: Date.now(), cost: 5, state: 'Nueva', description: 'hola', observation: 'en orden',
    //       products: [
    //         {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //         {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //         {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //         {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
    //       ]
    //     }
    //   );
  }

  updateApplication(application: any): Promise<any> {
    return this.http.put<any[]>(`${this.url}/applications`, application).toPromise();
  }

  // ---------------------------------------------------------------------------------------------------
  // CLIENT
  // ---------------------------------------------------------------------------------------------------

  getClientApplications(): Promise<any> {
    return this.http.get<any[]>(`${this.url}/client/${this.currentUser.id}/applications`).toPromise();

    // MOCK
    // return Promise.resolve(
    //   [
    //     {id: '0', date: Date.now(), state: 'Con Nota'},
    //   ]
    // );
  }

  getClientProducts(): Promise<any> {
    return this.http.get<any[]>(`${this.url}/client/${this.currentUser.id}/products`).toPromise();

    // MOCK
    // return Promise.resolve(
    //   [
    //     {id: 0, name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
    //     {id: 1, name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
    //     {id: 2, name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
    //     {id: 3, name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
    //   ]
    // );
  }

  createClientApplication(application: any): Promise<any> {
    return this.http.post<any[]>(`${this.url}/client/${this.currentUser.id}/applications`, application).toPromise();

    // return Promise.resolve({id: 99});
  }

  deleteClientApplication(application: any) {
    return this.http.post<any[]>(`${this.url}/client/${this.currentUser.id}/applications/${application.id}`, application).toPromise();

    // MOCK
    // return Promise.resolve(true);
  }

  // ---------------------------------------------------------------------------------------------------
  // MANAGER
  // ---------------------------------------------------------------------------------------------------

  getManagerApplications(): Promise<any> {
    return this.http.get<any[]>(`${this.url}/applications`).toPromise();

    // MOCK
    // return Promise.resolve(
    //   [
    //     {
    //       id: '0', client: 'Velazquez', date: Date.now(), cost: 3, state: 'En Almacen', description: 'asd', products:
    //         [
    //           {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
    //           {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
    //           {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
    //           {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
    //         ]
    //     },
    //     {
    //       id: '1', client: 'Bustamante', date: Date.now(), cost: 3, state: 'Aceptado', description: 'asd', products:
    //         [
    //           {name: 'Pasta', date: Date.now(), lot: 123, quantity: 5},
    //           {name: 'Choclo', date: Date.now(), lot: 123, quantity: 5},
    //           {name: 'Caca', date: Date.now(), lot: 123, quantity: 5},
    //           {name: 'Asd', date: Date.now(), lot: 123, quantity: 5}
    //         ]
    //     },
    //     {
    //       id: '2', client: 'Curat', date: Date.now(), cost: 3, state: 'En Almacen', description: 'asd',
    //       products: [
    //         {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //         {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //         {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //         {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5}
    //       ]
    //     },
    //     {
    //       id: '3', client: 'Curat', date: Date.now(), cost: 3, state: 'Nueva', description: 'asd',
    //       products: [
    //         {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //         {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //         {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //         {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5}
    //       ]
    //       },
    //       {
    //         id: '4', client: 'Curat', date: Date.now(), cost: 3, state: 'Rechazada', description: 'asd',
    //         products: [
    //           {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //           {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //           {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5},
    //           {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5}
    //         ]
    //       },
    //       {
    //         id: '5', client: 'Curat', date: Date.now(), cost: 3, state: 'Controlada', description: 'asd', observation: 'en orden',
    //         products: [
    //           {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
    //         ]
    //       },
    //       {
    //         id: '6', client: 'Curat', date: Date.now(), cost: 3, state: 'Controlada', description: 'asd', observation: 'en orden',
    //         products: [
    //           {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
    //         ]
    //       },
    //       {
    //         id: '7', client: 'Velazquez', date: Date.now(), cost: 3, state: 'Con Nota', description: 'asd', observation: 'en orden',
    //         products: [
    //           {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //           {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
    //         ]
    //       }
    //     ]
    //   );
  }

  createManagerNote(note: any): Promise<any> {
    return this.http.post<any[]>(`${this.url}/note`, note).toPromise();
    // return Promise.resolve(true);
  }

  getNote(applicationId: any): Promise<any> {
    return this.http.get<any[]>(`${this.url}/applications/${applicationId}/note`).toPromise();

    // return Promise.resolve(
    //   {
    //     note: {
    //       date: '2019-10-24',
    //       descriptionCredit: 'Ok',
    //       descriptionDebit: 'Algunos mal estado',
    //       priceCredit: 320,
    //       priceDebit: 40,
    //     },
    //     application: {
    //       id: '0', client: 'Velazquez', date: Date.now(), cost: 5, state: 'En Almacen', description: 'hola',
    //       observation: 'en orden',
    //       products: [
    //         {name: 'Pasta', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //         {name: 'Choclo', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //         {name: 'Caca', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3},
    //         {name: 'Asd', date: Date.now(), lot: 123, price: 20, weight: 2, quantity: 5, accepted: 4, good: 3}
    //       ]
    //     }
    //   }
    // );
  }
}
