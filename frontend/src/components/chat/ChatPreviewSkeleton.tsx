export default function ChatPreviewSkeleton() {
  return (
    <div className='block p-4 border-b border-gray-200 animate-pulse'>
      <div className='flex justify-between items-center mb-1'>
        <div className='h-5 w-1/3 bg-gray-300 rounded'></div>
        <div className='h-4 w-16 bg-gray-300 rounded'></div>
      </div>
      <div className='flex justify-between items-center'>
        <div className='h-4 w-2/3 bg-gray-300 rounded'></div>
        <div className='h-5 w-6 bg-gray-300 rounded-full'></div>
      </div>
    </div>
  );
}