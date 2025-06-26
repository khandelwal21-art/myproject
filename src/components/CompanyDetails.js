import React, { useState } from 'react'
import './TransportDestinations.css'
import { toast } from "react-toastify" 
import { useEffect } from 'react';

const categories = [
    "Manufacturer",
    "Transporter",
    "Truck Owner",
    "Individual",
    "Other",
  ];
function CompanyDetails({ data, setData,onNext }) {
 
    const [formData, setFormData] = useState({
        category:"",
        companyName:"",
        phoneNumber:"",
      });
      const [errors,setErrors]=useState({
      category:"",
      companyName:"",
      phoneNumber:"",
      general:"",
      })
    
  const handleChange=(e)=>{
   const{name,value}=e.target;
   setFormData({...formData
    ,[name]:value});

  // Clear any previous errors for this input
    setErrors((prev) => ({ ...prev, [name]: "" }))
  }

   useEffect(() => {
       // Populate form with existing data when entering this step
      if (data) {
        setFormData({
          category: data.category || "",
          companyName: data.companyName || "",
          phoneNumber: data.phoneNumber || "",
        })
      }
    }, [data])
  

   const handleSubmit=async(e)=>{
    e.preventDefault();
    let isValid = true
    const newErrors = { category: "", companyName: "", phoneNumber: "",general: "" }

    if (!formData.category.trim()) {
      newErrors.category = "Catagory is required"
      isValid = false
    }

    if (!formData.companyName.trim()) {
      newErrors.companyName = "Company Name is required"
      isValid = false
    }
    if (!formData.phoneNumber.trim()) {
      newErrors.phoneNumber = "phone Number  is required"
      isValid = false
    }
    if (!isValid) {
      setErrors(newErrors)
      return
    }
    

    if(!formData.category || !formData.companyName || !formData.phoneNumber) {
      setErrors({general: "All fields are required."});
      toast.error('Please fill all fields');
      return;
    }
    setData((prev) => ({
      ...prev,
      ...formData
    }))
     onNext(formData);
    }

   
  
  return (
    <div className="mt-3 flex justify-center  items-center">
      {errors.general && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded-md">{errors.general}</div>}
    <form
      onSubmit={handleSubmit}
      className="flex flex-col mb-3 pr-20 bg-white p-6 sm:p-8 w-full rounded-lg shadow-lg max-w-sm "
    >
      <div className="mt-1  ">
      <h1 className='lg:text-3xl md:text-2xl text-xl mb-2'><b>Book Your Truck</b></h1>
</div> 
      {/* Category Dropdown */}
      <label
        htmlFor="category"
        className="block  text-gray-700 font-base mb-2 mt-3 font-semibold"
      >
       Buisness Category
      </label>
      <select
        id="category"
        name="category"
        value={formData.category}
       onChange={handleChange}
        required
        className="lg:w-80 md:w-80 w-auto px-3 py-2 border rounded-md bg-gray-50 hover:bg-white text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75 "
      >    

<option value='' disabled>
Select Your Business Catagory
</option>
        {categories.map((cat) => (
          <option key={cat} value={cat}>
            {cat}
          </option>
        ))}
      </select>

      {/* Company Name Input */}
      <label
        htmlFor="companyName"
        className="block text-gray-700 mt-3 font-semibold text-base mb-1"
      >
        Company Name
      </label>
      <input
        type="text"
        id="companyName"
        name="companyName"
        value={formData.companyName}
        onChange={handleChange}
        placeholder="Enter company name"
        required
        className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
      />

      {/* Phone Number Input */}
      <label
        htmlFor="phoneNumber"
        className="block text-gray-700 font-medium mb-1 mt-3"
      >
        Phone Number
      </label>
      <input
        type="tel"
        id="phoneNumber"
        name="phoneNumber"
        value={formData.phoneNumber}
        onChange={handleChange}
        placeholder="Enter phone number"
        required
        title="Phone number should be  10 digits  "
        className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
      />

      {/* Next Button */}
      {/* <button
        type="submit"
        className="w-full bg-black  text-white font-semibold py-2 rounded-md transition-colors duration-200"
      >
        Next
      </button> */}
       <div className="mt-auto pt-6">
               <button
                type="submit"
                className="w-80 mt-48 bg-blue-600 text-white px-4 py-3 rounded-md hover:bg-blue-700 transition-colors font-medium"
                >
              Continue
              </button>
              </div>
    </form>
  </div>
  )
}

export default CompanyDetails
