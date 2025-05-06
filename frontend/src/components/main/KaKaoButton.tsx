'use client';

import Image from 'next/image';
import Button from '../common/Button';
import { getKakaoAuthUrl } from '@/utils/Kakao';

export default function KakaoButton() {

  const openWindow = () => {
    window.location.href = getKakaoAuthUrl();
  }

  return (
    <Button onClick={openWindow} className='gap-3.5 bg-[#FEE500] w-44 px-4 py-2'>
      <div className='flex items-center justify-center gap-1'>
        <Image src='/chat-logo.svg' alt='카카오 로고' width={30} height={30} color='brown' />
        <span className='text-md text-black'>카카오 로그인</span>
      </div>
    </Button>
  );
}
