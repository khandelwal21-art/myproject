import './App.css';
import TransportDestinations from './components/TransportDestinations';
import CompanyDetails from './components/CompanyDetails'
import { ToastContainer } from 'react-toastify';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import Loginportal from './components/Loginportal';
import BookingSummary from './components/BookingSummary';
import ForgotPassword from './components/ForgotPassword';
function App() {
  return (
    <Router> 
      <div>     
      <ToastContainer position="top-right" autoClose={3000} />
      </div>
      <Routes>
      <Route path='/Transportdestination' element={<TransportDestinations/>}></Route>
      <Route path='/' element={<HomePage/>}></Route>
      <Route path='/CompanyDetails' element={<CompanyDetails/>}></Route>
      <Route path='/Loginportal' element={<Loginportal/>}></Route>
      <Route path='/BookingSummary' element={<BookingSummary/>}></Route>
      <Route path="/forgot-password" element={<ForgotPassword/>}></Route>

      </Routes>
        
        </Router>
  );
}

export default App;
