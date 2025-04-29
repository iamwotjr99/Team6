'use client';

import BackHeader from '@/components/layout/header/BackHeader';
import {useRouter} from 'next/navigation';
import YouMessage from '@/components/chat/YouMessage';
import MeMessage from '@/components/chat/MeMessage';
import Inputbar from '@/components/chat/Inputbar';

export default function ChatBotItem() {
  const router = useRouter();

  const handleBack = () => {
    router.push('/chat');

  }
  return (
    <section className="h-full overflow-y-auto">
      <BackHeader mainText={'나만의 Ai 비서와 채팅'} onClick={handleBack}/>
      <div className='px-1 py-2'>
        <MeMessage message='한국에 대해 알려줘' time='2025-04-02'/>
        <YouMessage message="한국은 동북아시아에 위치한 나라로.." time='2025-04-02' nickname={''} />
      </div>
      <Inputbar />
    </section>
  )
}