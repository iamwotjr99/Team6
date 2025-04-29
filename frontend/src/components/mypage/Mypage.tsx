'use client';

import { useModalStore } from '@/stores/Modal';
import SelectHeader from '../layout/header/SelectHeader';
import MyInfo from './MyInfo';
import Setting from './Setting';
import LogoutCheckModal from './LogoutCheckModal';

export default function Mypage() {
    const setModal = useModalStore.use.setModal();
  
    const handleLogoutModal = () => {
      setModal(<LogoutCheckModal/>); 
    };
    
  return (
    <>
      <SelectHeader
        mainText='마이 페이지'
        buttonText='Logout'
        onClick={handleLogoutModal}
      />
      <section className='h-full overflow-y-auto'>
        <MyInfo />
        <Setting />
      </section>
    </>
  );
}
