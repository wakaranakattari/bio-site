'use client';

import { ReactNode } from 'react';
import { motion } from 'framer-motion';

interface AnimatedButtonProps {
  children: ReactNode;
  href?: string;
  onClick?: () => void;
  variant?: 'primary' | 'secondary' | 'outline';
  className?: string;
}

export function AnimatedButton({
  children,
  href,
  onClick,
  variant = 'primary',
  className = '',
}: AnimatedButtonProps) {
  const baseClasses =
    'relative px-6 py-3 rounded-xl font-medium transition-all duration-300 overflow-hidden group flex items-center justify-center gap-2';

  const variants = {
    primary: 'bg-[#2959aa] dark:bg-[#7aa2f7] text-white hover:shadow-lg',
    secondary:
      'glass-effect text-[#343b58] dark:text-[#c0caf5] hover:bg-white/5 dark:hover:bg-black/5',
    outline:
      'border border-[#2959aa]/30 dark:border-[#7aa2f7]/30 text-[#2959aa] dark:text-[#7aa2f7] hover:bg-[#2959aa]/5 dark:hover:bg-[#7aa2f7]/5',
  };

  const content = (
    <>
      <span className="relative z-10">{children}</span>
      <motion.div
        className="absolute inset-0 bg-gradient-to-r from-[#2959aa] to-[#5a3e8e] dark:from-[#7aa2f7] dark:to-[#bb9af7] opacity-0 group-hover:opacity-10 transition-opacity duration-300"
        initial={false}
      />
    </>
  );

  if (href) {
    return (
      <motion.a
        href={href}
        className={`${baseClasses} ${variants[variant]} ${className}`}
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
      >
        {content}
      </motion.a>
    );
  }

  return (
    <motion.button
      onClick={onClick}
      className={`${baseClasses} ${variants[variant]} ${className}`}
      whileHover={{ scale: 1.05 }}
      whileTap={{ scale: 0.95 }}
    >
      {content}
    </motion.button>
  );
}
