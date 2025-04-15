export default function MyInfo() {
  return (
    <section className='mx-2 mt-2 p-4 border rounded-2xl gap-4'>
      <span className='text-lg font-bold font-mono'>유저 이름</span>
      <p className="text-sm font-medium text-gray-700 py-1">
        닉네임님의 가입일은 <span className="text-red-400">2025년 00월 00일</span> 입니다.
      </p>
      <div className='pt-4 pb-2 items-center justify-center flex flex-row gap-12'>
        <div>
          <span className='text-base items-center flex justify-center font-bold'>3개</span>
          <p className='text-sm font-extrabold text-gray-700'>내가 만든 Chat</p>
        </div>
        <div>
          <span className='text-base items-center flex justify-center font-bold'>3개</span>
          <p className='text-sm font-extrabold text-gray-700'>들어간 Chat</p>
        </div>
        <div>
          <span className='text-base items-center flex justify-center font-bold'>3개</span>
          <p className='text-sm font-extrabold text-gray-700'>내가 보낸 Msg</p>
        </div>
      </div>
    </section>
  );
}