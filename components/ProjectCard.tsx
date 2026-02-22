'use client';

import { GitHubRepo } from '@/types';
import { Star, GitFork, Calendar, Shield, ExternalLink } from 'lucide-react';
import { formatDate } from '@/lib/utils';
import { LanguageBar } from './LanguageBar';
import { useLanguage } from '@/components/LanguageProvider';

interface ProjectCardProps {
  project: GitHubRepo;
}

export function ProjectCard({ project }: ProjectCardProps) {
  const { t } = useLanguage();

  return (
    <div className="card p-6 rounded-2xl hover:border-accent/50 transition-all duration-300 group h-full flex flex-col">
      <div className="flex justify-between items-start mb-4 h-[60px]">
        <h3 className="text-xl font-bold font-mono break-words pr-4 line-clamp-2">
          <a
            href={project.html_url}
            target="_blank"
            rel="noopener noreferrer"
            className="hover:text-accent transition-colors flex items-center gap-2"
          >
            {project.name}
            <ExternalLink className="w-3 h-3 opacity-0 group-hover:opacity-100 transition-opacity shrink-0" />
          </a>
        </h3>
        <div className="flex items-center space-x-4 shrink-0">
          <div className="flex items-center space-x-1">
            <Star className="w-4 h-4 text-yellow-500 shrink-0" />
            <span className="font-mono text-sm">{project.stargazers_count}</span>
          </div>
          <div className="flex items-center space-x-1">
            <GitFork className="w-4 h-4 text-green-500 shrink-0" />
            <span className="font-mono text-sm">{project.forks_count}</span>
          </div>
        </div>
      </div>

      <div className="mb-4 h-[72px]">
        <p className="text-muted-foreground text-sm line-clamp-3">
          {project.description || t('projects.noDescription')}
        </p>
      </div>

      <div className="mb-4 h-[30px]">
        {project.languages && Object.keys(project.languages).length > 0 ? (
          <LanguageBar languages={project.languages} />
        ) : (
          <div className="h-[4px] w-full bg-muted/30 rounded-full mt-3" />
        )}
      </div>

      <div className="flex flex-wrap items-center justify-between pt-4 border-t border-border mt-auto">
        <div className="flex items-center space-x-4">
          <div className="flex items-center space-x-1">
            <Calendar className="w-4 h-4 text-accent shrink-0" />
            <span className="text-xs font-mono whitespace-nowrap">
              {formatDate(project.updated_at)}
            </span>
          </div>

          {project.license ? (
            <div className="flex items-center space-x-1">
              <Shield className="w-4 h-4 text-purple-500 shrink-0" />
              <span className="text-xs font-mono max-w-[100px] truncate">
                {project.license.name}
              </span>
            </div>
          ) : (
            <div className="w-16" />
          )}
        </div>

        <div className="flex items-center space-x-2">
          {project.topics && project.topics.length > 0 ? (
            project.topics.slice(0, 2).map((topic) => (
              <span
                key={topic}
                className="px-2 py-1 text-xs border border-border bg-muted rounded font-mono truncate max-w-[80px]"
              >
                {topic}
              </span>
            ))
          ) : (
            <div className="w-12" />
          )}
        </div>
      </div>
    </div>
  );
}