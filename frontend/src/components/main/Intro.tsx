import { IoChatbubblesSharp } from 'react-icons/io5';
import KakaoButton from '@/components/main/KaKaoButton';

export default function Intro() {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center gap-4 px-4 text-center">
      <IoChatbubblesSharp size={200} className='text-green-700'/>
      <h2 className="text-xl font-bold">Seis에 오신 것을 환영합니다.</h2>
      <KakaoButton />
    </div>
  );
}