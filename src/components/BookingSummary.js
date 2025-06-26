

function BookingSummary({ formData  }) {

  if (!formData) {
    console.log(formData);
    return <div className="text-center mt-4 text-gray-500">Loading summary...</div>;
   
  }
  return (
    <div className="h-[600px] w-[500px]  max-w-md mx-auto bg-white shadow-lg rounded-xl p-6 mt-8">
      <h2 className="text-2xl font-semibold text-blue-600 mb-4">Booking Details</h2>
      <div className="space-y-2 text-gray-700">
        <p><strong>Company Name:</strong> {formData.companyName}</p>
        <p><strong>Phone Number:</strong> {formData.phoneNumber}</p>
        <p><strong>Category:</strong> {formData.category}</p>
        <p><strong>Source:</strong> {formData.source}</p>
        <p><strong>Destination:</strong> {formData.destination}</p>
        <p><strong>Transport Type:</strong> {formData.transportType}</p>
        <p><strong>Weight:</strong> {formData.weight} {formData.weightUnit}</p>
        <p><strong>Length:</strong> {formData.length}</p>
        <p><strong>Height:</strong> {formData.height}</p>

      </div>
    </div>
  );
}
  export default BookingSummary