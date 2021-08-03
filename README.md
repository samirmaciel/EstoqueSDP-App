![GitHub Cards Preview](https://github.com/samirmaciel/EstoqueSDP-App/blob/master/estoque_banner_github.png)

# Estoque
Controle e localização de calçados em estoque. 


## Desenvolvido com 🛠
- [Java](https://www.java.com/pt-BR/) - Uma das linguagens oficiais para desenvolvimento Android.
- [FireBase](https://firebase.google.com/?hl=pt-br) - Baas (Backend as a Service) para aplicações Web e Mobile do Google


## Estrutura de pacotes 📦
    
    com.samirmaciel.payflow_kotlin # Root Package
    ├── modules                       
    │   ├── barcodescanner
    |   |   ├── BarcodeScannerActivity
    |   ├── bottomsheetdialog_payment
    |   |   ├── BottomSheetDialogPayment
    |   |   ├── BottomSheetDialogPaymentViewModel
    |   |── bottomsheetdialog_statiment
    |   |   ├── BottomSheetDialogStatiment
    │   ├── home
    |   |   ├── HomeActivity
    |   |   ├── HomeViewModel
    |   ├── login
    |   |   ├── LoginActivity
    |   |── mypayments
    |   |   ├── MyPaymentsSlipsFragment
    |   |   ├── MyPaymentsSlipsViewModel
    │   ├── mystatiments  
    |   |   ├── MyStatimentsFragment
    |   |   ├── MyStatimentsViewModel
    |   ├── register    
    |   |   ├── RegisterActivity
    |   |   ├── RegistrationViewModel
    |   |── splash
    |   |   ├── SplashActivity
    |
    ├── shared               
    │   ├── commom  
    |   |   ├── DateTextWatcher
    |   |   ├── MoneyTextWatcher
    |   |   ├── PaymentsRecyclerViewAdapter
    |   |   ├── StatimentsRecyclerViewAdapter
    |   ├── data 
    |   |   ├── AppDataBase
    |   |   ├── PaymentSlipDataSource
    |   |   ├── StatimentDataSource
    |   |── model 
    |   |   ├── AppDataBase
    |   |   |   ├── PaymentSlipRepository
    |   |   |   ├── RegistrationViewParams
    |   |   |   ├── StatimentRepository
    |   |   ├── PaymentSlipDataSource
    |   |   |   ├── PaymentSlip
    |   |   |   ├── PaymentSlipDAO
    |   |   |   ├── PaymentSlipEntity
    |   |   ├── StatimentDataSource
    |   |   |   ├── Statiment
    |   |   |   ├── StatimentDAO
    |   |   |   ├── StatimentEntity
  


<br />

## Arquitetura 🗼
Para esse app foi utilizado o padrão [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch).

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png?hl=pt-br)
