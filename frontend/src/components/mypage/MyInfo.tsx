'use client';

import { useEffect, useState } from 'react';
import { useAuthStore } from '@/stores/Auth';
import { fetchMyInfo } from '@/app/api/auth';

export default function MyInfo() {
  const kakaoEmailInStore = useAuthStore.use.kakaoEmail();
  const [nickname, setNickname] = useState('');
  const [kakaoEmail, setKakaoEmail] = useState('');

  useEffect(() => {
    const loadMyInfo = async () => {
      if (!kakaoEmailInStore) return;

      try {
        const data = await fetchMyInfo();
        setNickname(data.nickname);
        setKakaoEmail(data.kakaoEmail);

      } catch (error) {
        console.error('loadMyInfo error:', error);
      }
    };

    loadMyInfo();
  }, [kakaoEmailInStore]);

  return (
    <section className='mx-2 mt-2 p-4 border rounded-2xl border-gray-300 bg-white text-black transition-colors duration-500'>
      <span className='text-lg font-bold font-mono'>
        {nickname || '유저 이름'}
      </span>

      <p className='text-sm font-medium text-gray-700 py-1'>
        {nickname || '닉네임'}의 이메일은{' '}
        <span className='text-red-400'>{kakaoEmail || '이메일 없음'}</span>{' '}
        입니다.
      </p>
     
      <div className='pt-4 pb-2 flex items-center justify-center gap-12'>
        <div>
          <span className='flex justify-center text-base font-bold'>3개</span>
          <p className='text-sm font-extrabold text-gray-700'>
            내가 만든 Chat
          </p>
        </div>
        <div>
          <span className='flex justify-center text-base font-bold'>3개</span>
          <p className='text-sm font-extrabold text-gray-700'>
            들어간 Chat
          </p>
        </div>
        <div>
          <span className='flex justify-center text-base font-bold'>3개</span>
          <p className='text-sm font-extrabold text-gray-700'>
            내가 보낸 Msg
          </p>
        </div>
      </div>
    </section>
  );
}
