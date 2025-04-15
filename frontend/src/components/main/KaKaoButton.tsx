'use client';

import Image from 'next/image';
import Link from 'next/link';



export default function KakaoButton() {
  return (
    <Link href="/chat">
      <button
        className="flex items-center gap-3.5 rounded-[12px] bg-[#FEE500] w-40 px-4 py-2 text-black"
      >
        <Image
          src="/chat-logo.svg" // public 폴더에 있어야 함
          alt="카카오 로고"
          width={24}
          height={24}
        />
        <span className="text-sm">카카오 로그인</span>
      </button>
    </Link>
  );
}