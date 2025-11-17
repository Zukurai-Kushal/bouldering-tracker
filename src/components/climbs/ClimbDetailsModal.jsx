import { StarIcon } from '@heroicons/react/24/solid';
import { useState } from 'react';

export default function ClimbDetailsModal({ climb, onClose, isPrivate = false, onEdit }) {
  const [showLocationPopup, setShowLocationPopup] = useState(false);

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white dark:bg-gray-800 rounded-lg p-4 w-11/12 max-w-lg relative">
        {/* Close Button */}
        <button
          onClick={onClose}
          className="absolute top-2 right-2 text-gray-500 hover:text-red-500"
        >
          ✕
        </button>

        {/* Row 1: Grade, Boulder Name, Rating */}
        <div className="flex justify-between items-center mb-3">
          <span className="text-lg font-bold">{climb.grade}</span>
          <span className="text-lg">{climb.boulderName}</span>
          <div className="flex">
            {[...Array(climb.rating)].map((_, i) => (
              <StarIcon key={i} className="h-5 w-5 text-yellow-400" />
            ))}
          </div>
        </div>

        {/* Row 2: Large Photo */}
        {climb.photoURL && (
          <img
            src={climb.photoURL}
            alt={climb.boulderName}
            className="w-full h-64 object-cover rounded-md mb-3"
          />
        )}

        {/* Row 3: Username, Location, Datetime */}
        <div className="flex justify-between items-center mb-3 text-sm">
          <span>{climb.username}</span>
          <span
            className="cursor-pointer underline"
            onMouseEnter={() => setShowLocationPopup(true)}
            onMouseLeave={() => setShowLocationPopup(false)}
          >
            {climb.location?.name || 'No location'}
          </span>
          <span className="text-gray-500">{new Date(climb.datetime).toLocaleString()}</span>
        </div>

        {/* Location Popup */}
        {showLocationPopup && climb.location && (
          <div className="absolute top-20 right-4 bg-white dark:bg-gray-700 shadow-lg rounded-md p-3 w-64 z-50">
            <div className="flex">
              {/* LHS: Photo */}
              <div className="w-20 h-20 bg-gray-200 dark:bg-gray-600 rounded-md overflow-hidden">
                {climb.location.locationPhotoUrl && (
                  <img
                    src={climb.location.locationPhotoUrl}
                    alt={climb.location.name}
                    className="w-full h-full object-cover"
                  />
                )}
              </div>
              {/* RHS: Details */}
              <div className="ml-2 text-xs">
                <div className="font-bold">{climb.location.name}</div>
                <div>{climb.location.type}</div>
                <div>{climb.location.country}, {climb.location.region}</div>
                <div>Lat: {climb.location.gpsLat}, Long: {climb.location.gpsLong}</div>
              </div>
            </div>
          </div>
        )}

        {/* Row 4: Comment */}
        {climb.comment && (
          <div className="mb-3">
            <span className="font-semibold">Comment:</span> {climb.comment}
          </div>
        )}

        {/* Row 5: Attempts */}
        <div className="mb-3">
          <span className="font-semibold">Attempts:</span> {climb.attempts}
        </div>

        {/* Row 6: Features */}
        <div className="flex flex-wrap gap-2">
          {climb.features.map(f => (
            <span
              key={f.name}
              className="bg-blue-100 dark:bg-blue-700 px-2 py-1 rounded-full text-xs"
            >
              {f.name}
            </span>
          ))}
        </div>

        {/* Edit button for climb owner */}
        {isPrivate && (
          <button
            onClick={onEdit}
            className="mt-4 bg-green-500 text-white px-3 py-2 rounded-md"
          >
            Edit Climb
          </button>
        )}
      </div>
    </div>
  );
}